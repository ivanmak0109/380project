package hkmu.wadd.dao;

import hkmu.wadd.exception.AttachmentNotFound;
import hkmu.wadd.exception.CommentNotFound;
import hkmu.wadd.exception.LectureNotFound;
import hkmu.wadd.model.Attachment;
import hkmu.wadd.model.Comment;
import hkmu.wadd.model.Lecture;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class LectureService {
    @Resource
    private LectureRepository lRepo;

    @Resource
    private AttachmentRepository aRepo;

    @Resource
    private CommentRepository cRepo;

    @Transactional
    public List<Lecture> getLectures() {
        return lRepo.findAll();
    }

    @Transactional
    public Lecture getLecture(long id)
            throws LectureNotFound {
        Lecture lecture = lRepo.findById(id).orElse(null);
        if (lecture == null) {
            throw new LectureNotFound(id);
        }
        return lecture;
    }

    @Transactional
    public Attachment getAttachment(long lectureId, UUID attachmentId)
            throws LectureNotFound, AttachmentNotFound {
        Lecture lecture = lRepo.findById(lectureId).orElse(null);
        if (lecture == null) {
            throw new LectureNotFound(lectureId);
        }
        Attachment attachment = aRepo.findById(attachmentId).orElse(null);
        if (attachment == null) {
            throw new AttachmentNotFound(attachmentId);
        }
        return attachment;
    }

    @Transactional(rollbackFor = LectureNotFound.class)
    public void delete(long id) throws LectureNotFound {
        Lecture deletedLecture = lRepo.findById(id).orElse(null);
        if (deletedLecture == null) {
            throw new LectureNotFound(id);
        }
        lRepo.delete(deletedLecture);
    }

    @Transactional(rollbackFor = AttachmentNotFound.class)
    public void deleteAttachment(long lectureId, UUID attachmentId)
            throws LectureNotFound, AttachmentNotFound {
        Lecture lecture = lRepo.findById(lectureId).orElse(null);
        if (lecture == null) {
            throw new LectureNotFound(lectureId);
        }
        for (Attachment attachment : lecture.getAttachments()) {
            if (attachment.getId().equals(attachmentId)) {
                lecture.deleteAttachment(attachment);
                lRepo.save(lecture);
                return;
            }
        }
        throw new AttachmentNotFound(attachmentId);
    }

    @Transactional(rollbackFor = CommentNotFound.class)
    public void deleteComment(long lectureId, long commentId)
            throws LectureNotFound, CommentNotFound {
        Lecture lecture = lRepo.findById(lectureId).orElse(null);
        if (lecture == null) {
            throw new LectureNotFound(lectureId);
        }
        for (Comment comment : lecture.getComments()) {
            if (comment.getId() == commentId) {
                lecture.deleteComment(comment);
                lRepo.save(lecture);
                return;
            }
        }
        throw new CommentNotFound(commentId);
    }

    @Transactional
    public long createLecture(String userName, String title, List<MultipartFile> attachments)
            throws IOException {
        Lecture lecture = new Lecture();
        lecture.setUserName(userName);
        lecture.setTitle(title);

        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setLecture(lecture);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                lecture.getAttachments().add(attachment);
            }
        }
        Lecture savedLecture = lRepo.save(lecture);
        return savedLecture.getId();
    }

    @Transactional(rollbackFor = LectureNotFound.class)
    public void updateLecture(long id, String title, List<MultipartFile> attachments)
            throws IOException, LectureNotFound {
        Lecture updatedLecture = lRepo.findById(id).orElse(null);
        if (updatedLecture == null) {
            throw new LectureNotFound(id);
        }
        updatedLecture.setTitle(title);
        for (MultipartFile filePart : attachments) {
            Attachment attachment = new Attachment();
            attachment.setName(filePart.getOriginalFilename());
            attachment.setMimeContentType(filePart.getContentType());
            attachment.setContents(filePart.getBytes());
            attachment.setLecture(updatedLecture);
            if (attachment.getName() != null && attachment.getName().length() > 0
                    && attachment.getContents() != null
                    && attachment.getContents().length > 0) {
                updatedLecture.getAttachments().add(attachment);
            }
        }
        lRepo.save(updatedLecture);
    }

    @Transactional(rollbackFor = LectureNotFound.class)
    public void createComment(long lectureId, String userName,String newComment)
            throws IOException, LectureNotFound {
        Lecture updatedLecture = lRepo.findById(lectureId).orElse(null);
        if (updatedLecture == null) {
            throw new LectureNotFound(lectureId);
        }
        Comment comment = new Comment();
        comment.setComment(newComment);
        comment.setUserName(userName);
        comment.setLecture(updatedLecture);
        updatedLecture.getComments().add(comment);
        lRepo.save(updatedLecture);
    }
}