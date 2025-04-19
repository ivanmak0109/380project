package hkmu.wadd.controller;

import hkmu.wadd.dao.LectureService;
import hkmu.wadd.exception.AttachmentNotFound;
import hkmu.wadd.exception.CommentNotFound;
import hkmu.wadd.exception.LectureNotFound;
import hkmu.wadd.model.Attachment;
import hkmu.wadd.model.Lecture;
import hkmu.wadd.view.DownloadingView;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/lecture")
public class LectureController {

    @Resource
    private LectureService lService;

    // Controller methods, Form-backing object, ...
    @GetMapping(value = {"", "/list"})
    public String list(ModelMap model) {
        model.addAttribute("lectureDatabase", lService.getLectures());
        return "list";
    }

    @GetMapping("/create")
    public ModelAndView create() {
        return new ModelAndView("add", "lectureForm", new Form());
    }

    public static class Form {
        private String title;
        private List<MultipartFile> attachments;

        // Getters and Setters of title, attachments
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<MultipartFile> getAttachments() {
            return attachments;
        }

        public void setAttachments(List<MultipartFile> attachments) {
            this.attachments = attachments;
        }
    }

    @PostMapping("/create")
    public View create(Form form, Principal principal) throws IOException {
        long lectureId = lService.createLecture(principal.getName(),
                form.getTitle(), form.getAttachments());
        return new RedirectView("/lecture/view/" + lectureId, true);
    }

    @GetMapping("/view/{lectureId}")
    public ModelAndView view(@PathVariable("lectureId") long lectureId,
                             ModelMap model) throws LectureNotFound{
        Lecture lecture = lService.getLecture(lectureId);
        model.addAttribute("lectureId", lectureId);
        model.addAttribute("lecture", lecture);
        return new ModelAndView("view", "commentForm", new CommentForm());
    }

    public static class CommentForm {
        private String comment;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    @PostMapping("/view/{lectureId}")
    public View createComment(@PathVariable("lectureId") long lectureId, CommentForm form,
                              Principal principal)
            throws IOException,LectureNotFound {
        Lecture lecture = lService.getLecture(lectureId);
        if (lecture == null) {
            return new RedirectView("/lecture/list",true);
        }
        lService.createComment(lectureId, principal.getName(), form.getComment());
        return new RedirectView("/lecture/view/" + lectureId, true);
    }

    @GetMapping("/{lectureId}/attachment/{attachment:.+}")
    public View download(@PathVariable("lectureId") long lectureId,
                         @PathVariable("attachment") UUID attachmentId)
            throws LectureNotFound, AttachmentNotFound {
        Attachment attachment = lService.getAttachment(lectureId, attachmentId);
        return new DownloadingView(attachment.getName(),
                attachment.getMimeContentType(), attachment.getContents());
    }

    @GetMapping("/delete/{lectureId}")
    public String deleteLecture(@PathVariable("lectureId") long lectureId)
            throws LectureNotFound {
        lService.delete(lectureId);
        return "redirect:/lecture/list";
    }

    @GetMapping("/{lectureId}/delete/{attachment:.+}")
    public String deleteAttachment(@PathVariable("lectureId") long lectureId,
                                   @PathVariable("attachment") UUID attachmentId)
            throws LectureNotFound, AttachmentNotFound {
        lService.deleteAttachment(lectureId, attachmentId);
        return "redirect:/lecture/view/" + lectureId;
    }

    @GetMapping("/{lectureId}/deleteComment/{commentId}")
    public String deleteComment(@PathVariable("lectureId") long lectureId,
                                   @PathVariable("commentId") long commentId)
            throws LectureNotFound, CommentNotFound {
        lService.deleteComment(lectureId, commentId);
        return "redirect:/lecture/view/" + lectureId;
    }

    @GetMapping("/edit/{lectureId}")
    public ModelAndView showEdit(@PathVariable("lectureId") long lectureId,
                                 Principal principal, HttpServletRequest request)
            throws LectureNotFound {
        Lecture lecture = lService.getLecture(lectureId);
        if (lecture == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(lecture.getUserName()))) {
            return new ModelAndView(new RedirectView("/lecture/list", true));
        }

        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("lecture", lecture);

        Form lectureForm = new Form();
        lectureForm.setTitle(lecture.getTitle());
        modelAndView.addObject("lectureForm", lectureForm);

        return modelAndView;
    }

    @PostMapping("/edit/{lectureId}")
    public String edit(@PathVariable("lectureId") long lectureId, Form form,
                       Principal principal, HttpServletRequest request)
            throws IOException, LectureNotFound {
        Lecture lecture = lService.getLecture(lectureId);
        if (lecture == null
                || (!request.isUserInRole("ROLE_ADMIN")
                && !principal.getName().equals(lecture.getUserName()))) {
            return "redirect:/lecture/list";
        }

        lService.updateLecture(lectureId, form.getTitle(), form.getAttachments());
        return "redirect:/lecture/view/" + lectureId;
    }

    @ExceptionHandler({LectureNotFound.class, AttachmentNotFound.class})
    public ModelAndView error(Exception e) {
        return new ModelAndView("error", "message", e.getMessage());
    }
}