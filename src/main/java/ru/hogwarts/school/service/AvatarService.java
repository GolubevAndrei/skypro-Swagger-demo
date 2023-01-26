package ru.hogwarts.school.service;

import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {
    void uploadAvatar(MultipartFile avatar);

    Pair<String, byte[]> readAvatarFromDb(long id);

    org.springframework.data.util.Pair<String, byte[]> readAvatarFromFs(long id);
}
