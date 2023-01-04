package com.webproject.service;

import com.webproject.domain.Post;
import com.webproject.domain.User;
import com.webproject.dto.request.PostCreateRequest;
import com.webproject.dto.request.PostUpdateRequest;
import com.webproject.repository.PostRepository;
import com.webproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService){
        this.postRepository=postRepository;
        this.userService=userService;
    }

    public List<Post> getAllPost(Optional<Long> userId) {
       if(userId.isPresent())
           return postRepository.findByUserId(userId.get());

       return postRepository.findAll();
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
       User user = userService.getOneUser(newPostRequest.getUserId());
       if(user == null)
           return null;
       Post toSave = new Post();
       toSave.setId(newPostRequest.getId());
       toSave.setTitle(newPostRequest.getTitle());
       toSave.setText(newPostRequest.getText());
       toSave.setUser(user);

        return postRepository.save(toSave);
    }

    public Post updateOnePost(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post toupdate = post.get();
            toupdate.setTitle(postUpdateRequest.getTitle());
            toupdate.setText(postUpdateRequest.getText());
            postRepository.save(toupdate);
            return toupdate;
        }
        return null;
    }

    public void deleteOnePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
