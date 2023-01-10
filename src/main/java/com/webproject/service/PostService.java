package com.webproject.service;

import com.webproject.domain.Post;
import com.webproject.domain.User;
import com.webproject.dto.request.PostCreateRequest;
import com.webproject.dto.request.PostUpdateRequest;
import com.webproject.dto.response.PostResponse;
import com.webproject.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService){
        this.postRepository=postRepository;
        this.userService=userService;
    }

    public List<PostResponse> getAllPost(Optional<Long> userId) {
        List<Post> list;
       if(userId.isPresent()){
           list = postRepository.findByUserId(userId.get());
       }
       list = postRepository.findAll();

       return list.stream().map(p-> new PostResponse(p)).collect(Collectors.toList());
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostRequest) {
       User user = userService.getOneUserById(newPostRequest.getUserId());
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
