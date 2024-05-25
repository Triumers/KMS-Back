package org.triumers.kmsback.common.translation.service;

import org.triumers.kmsback.common.translation.dto.PostDTO;

public interface PostService {
    PostDTO getTranslatedPost(int postId, String targetLang);
}
