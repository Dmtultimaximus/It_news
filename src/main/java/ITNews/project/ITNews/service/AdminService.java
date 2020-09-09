package ITNews.project.ITNews.service;

import ITNews.project.ITNews.dto.UsersForAdminResponse;
import ITNews.project.ITNews.model.AuthorityEntity;
import ITNews.project.ITNews.model.UserEntity;
import ITNews.project.ITNews.repository.AuthorityRepository;
import ITNews.project.ITNews.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public List getUsers() {
        return this.userRepository.findAll().stream()
                .map(this::mapForUsers)
                .collect(Collectors.toList());
    }

    public List getRoles() {
        return this.authorityRepository.findAll();
    }

    private UsersForAdminResponse mapForUsers(UserEntity userEntity) {
        return UsersForAdminResponse.builder().authority(userEntity.getAuthorities())
                .enabled(userEntity.isEnabled())
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .userId(userEntity.getUserId())
                .build();
    }

    public boolean delete(Long userId, Long roleId) {
        Optional<UserEntity> user = this.userRepository.getByUserId(userId);
        user.get().getAuthorities().removeIf(authorityEntity -> {
            return authorityEntity.getRoleId().equals(roleId);
        } );
        return true;
    }

    public boolean add(Long userId, Long roleId) {
        Optional<UserEntity> user = this.userRepository.getByUserId(userId);
        Optional<AuthorityEntity> role = this.authorityRepository.findByRoleId(roleId);
        user.get().getAuthorities().add(role.get());
        return true;
    }

}
