package wust.fxp.develop.platfrom.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import wust.fxp.develop.platfrom.dao.CategoryDAO;
import wust.fxp.develop.platfrom.entity.Category;

import java.util.List;

/**
 * @author Evan
 * @date 2019/4
 */
@Service
public class CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    public List<Category> list() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(sort);
    }

    public Category get(int id) {
        Category c= categoryDAO.findById(id);
        return c;
    }
}
