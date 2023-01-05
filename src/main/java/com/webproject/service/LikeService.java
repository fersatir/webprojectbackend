package com.webproject.service;

import com.webproject.domain.Like;
import com.webproject.domain.Post;
import com.webproject.domain.User;
import com.webproject.dto.request.LikeCreateRequest;
import com.webproject.dto.response.LikeResponse;
import com.webproject.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    private LikeRepository likeRepository;
    private UserService userService;
    private PostService postService;

    public LikeService(LikeRepository likeRepository,UserService userService, PostService postService){
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }


    public List<LikeResponse> getAllLikesWithParam(Optional<Long> userId, Optional<Long> postId) {
        List<Like>likes;
        User user = userService.getOneUserById(userId.get());
        Post post = postService.getOnePostById(postId.get());
        if(user != null && post != null){
            likes = likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
        }else if(post != null){
            likes = likeRepository.findByPostId(postId.get());
        }else if(user != null){
            likes = likeRepository.getByUserId(userId.get());
        }else
            likes = likeRepository.findAll();

        return likes.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like createOneLike(LikeCreateRequest request) {
      User user = userService.getOneUserById(request.getUserId());
      Post post = postService.getOnePostById(request.getPostId());
      if(user != null && post != null){
          Like likeToSave = new Like();
          likeToSave.setId(request.getId());
          likeToSave.setUser(user);
          likeToSave.setPost(post);
          return likeRepository.save(likeToSave);
      }else
        return null;
    }

    public Like getOneLikeById(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    public void deleteOneLikeById(Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
