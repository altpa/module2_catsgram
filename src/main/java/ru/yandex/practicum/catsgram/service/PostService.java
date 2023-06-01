package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exceptions.PostNotFoundException;
import ru.yandex.practicum.catsgram.exceptions.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(Integer size, String sort, Integer page) {
        if(!(sort.equals("asc") || sort.equals("desc"))){
            throw new IllegalArgumentException();
        }
        if(page < 0 || size <= 0){
            throw new IllegalArgumentException();
        }

        int from = page * size;

        return posts.stream().sorted((p0, p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate()); //прямой порядок сортировки
            if(sort.equals("desc")){
                comp = -1 * comp; //обратный порядок сортировки
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());

//        posts.sort((post1, post2) -> {
//            int result = 1;
//            if (post1.getCreationDate().isAfter(post2.getCreationDate())) {
//                result = -1;
//            }
//            if (post1.getCreationDate().equals(post2.getCreationDate())) {
//                result = 0;
//            }
//
//            if (sort.equals("asc")) {
//                return result;
//            } else return result * -1;
//        });
//
//            Map<Integer, List<Post>> pages = new HashMap<>();
//            Integer pageCounter = 1;
//            if (size < posts.size()) {
//                for (int i = size; i < posts.size(); i += size) {
//                    pages.put(pageCounter, posts.subList(0,i));
//                    pageCounter++;
//                }
//                return pages.get(page);
//            }
//            return posts;
    }

    public Post create(Post post) {
        User user = userService.findUserByEmail(post.getAuthor());
        if (user != null) {
        posts.add(post);
        return post;
        } else {
            throw new UserNotFoundException("Пользователь " + post.getAuthor() + " не найден");
        }
    }

    public Post getPostById(int id) {
        for (Post post: posts) {
            if (post.getId() == id) {
                return post;
            }
        }
        throw new PostNotFoundException("Пост с id: " + id + " не найден");
    }
}