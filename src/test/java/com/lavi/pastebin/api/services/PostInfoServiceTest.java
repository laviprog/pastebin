package com.lavi.pastebin.api.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.lavi.pastebin.api.models.PostInfo;
import com.lavi.pastebin.api.repositories.PostInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PostInfoServiceTest {

    @Mock
    private PostInfoRepository repository;

    @InjectMocks
    private PostInfoService service;

    private PostInfo postInfo;
    private final String hash = "testHash";

    @BeforeEach
    void setUp() {
        postInfo = new PostInfo();
        postInfo.setHash(hash);
    }

    @Test
    void testHasHashExists() {
        when(repository.findByHash(hash)).thenReturn(Optional.of(postInfo));

        boolean result = service.hasHash(hash);

        assertTrue(result);
        verify(repository, times(1)).findByHash(hash);
    }

    @Test
    void testHasHashNotExists() {
        when(repository.findByHash(hash)).thenReturn(Optional.empty());

        boolean result = service.hasHash(hash);

        assertFalse(result);
        verify(repository, times(1)).findByHash(hash);
    }

    @Test
    void testFindByHashExists() {
        when(repository.findByHash(hash)).thenReturn(Optional.of(postInfo));

        PostInfo result = service.findByHash(hash);

        assertNotNull(result);
        assertEquals(postInfo, result);
        verify(repository, times(1)).findByHash(hash);
    }

    @Test
    void testFindByHashNotExists() {
        when(repository.findByHash(hash)).thenReturn(Optional.empty());

        PostInfo result = service.findByHash(hash);

        assertNull(result);
        verify(repository, times(1)).findByHash(hash);
    }

    @Test
    void testSave() {
        when(repository.save(any(PostInfo.class))).thenReturn(postInfo);

        service.save(postInfo);

        verify(repository, times(1)).save(postInfo);
    }

    @Test
    void testDeleteById() {
        doNothing().when(repository).deleteById(any(Long.class));

        Long id = 1L;
        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }
}
