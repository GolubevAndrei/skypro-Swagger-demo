package ru.hogwarts.school.service;

import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;

import java.util.Collection;

public interface AvatarService {
    void uploadAvatar(MultipartFile avatar);

    Pair<String, byte[]> readAvatarFromDb(long id);

    org.springframework.data.util.Pair<String, byte[]> readAvatarFromFs(long id);

    ResponseEntity<Collection<Avatar>> getAll(Integer pageNumber, Integer pageSize);
}
