package com.system.artworkspace.integration;

import com.system.artworkspace.artwork.ArtworkEntity;
import com.system.artworkspace.exhibition.ExhibitionEntity;
import com.system.artworkspace.exhibition.ExhibitionMapper;
import com.system.artworkspace.exhibition.ExhibitionRepository;
import com.system.artworkspace.exhibition.ExhibitionService;
import com.system.artworkspace.user.UserEntity;
import com.system.artworkspace.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

@SpringBootTest
class ExhibitionServiceIntegrationTest {

    @Autowired
    private ExhibitionService exhibitionService;

    @Autowired
    private ExhibitionRepository exhibitionRepository;

    @MockBean
    private Logger logger;

    @Test
    public void shouldCleanupExpiredExhibitions() {
        exhibitionService.cleanupExpiredExhibitions();
    }
}
