package com.netflix_clone.boardservice.service;

import com.netflix_clone.boardservice.repository.boardProfileRepository.BoardProfileRepository;
import com.netflix_clone.boardservice.repository.domains.BoardProfile;
import com.netflix_clone.boardservice.repository.dto.reference.ProfileDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class BoardProfileService {
    private final BoardProfileRepository repository;
    private final ModelMapper mapper;

    public void saveProfile(ProfileDto profileDto){
        repository.save(mapper.map(profileDto, BoardProfile.class));
    }
}
