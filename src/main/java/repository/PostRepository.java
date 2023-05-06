package repository;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public interface PostRepository extends GenericRepository<Post, Long> {

}
