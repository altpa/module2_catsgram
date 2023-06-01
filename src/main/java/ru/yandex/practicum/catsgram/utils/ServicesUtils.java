package ru.yandex.practicum.catsgram.utils;

import ru.yandex.practicum.catsgram.model.Post;

import java.util.List;
import java.util.stream.Collectors;

public class ServicesUtils {
    public static List<Post> findAll(List<Post> posts, Integer size, String sort, Integer page) {
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
                })
                .skip(from)
                .limit(size)
                .collect(Collectors.toList());
    }
}
