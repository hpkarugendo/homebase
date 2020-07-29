package com.hpkarugendo.controllers;

import com.hpkarugendo.models.BlogPost;
import com.hpkarugendo.models.BlogPostCategory;
import com.hpkarugendo.models.Gallery;
import com.hpkarugendo.models.SiteAdmin;
import com.hpkarugendo.services.BlogPostService;
import com.hpkarugendo.services.HomebaseStorageService;
import com.hpkarugendo.services.SiteAdminService;
import com.microsoft.azure.storage.StorageException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Controller
public class BlogController {
    private SiteAdminService adminService;
    private BlogPostService postService;
    private HomebaseStorageService storageService;

    public BlogController(SiteAdminService adminService, BlogPostService postService, HomebaseStorageService storageService) {
        this.adminService = adminService;
        this.postService = postService;
        this.storageService = storageService;
    }

    @GetMapping("/blog")
    public String blogs(Model m){
        List<BlogPost> posts = postService.listPosts();

        if(posts.isEmpty()){
            m.addAttribute("bYes", false);
        } else {
            m.addAttribute("bYes", true);
            m.addAttribute("posts", posts);
        }

        return "blogs";
    }

    @GetMapping("/admin/blog/new")
    public String newBlog(Model m){
        BlogPost blogObject = new BlogPost();
        m.addAttribute("blogObject", blogObject);
        m.addAttribute("categories", postService.listCategories());

        return "blogs_new";
    }

    @PostMapping("/admin/blog/add")
    public String savePost(BlogPost blogObject, RedirectAttributes ra, @PathParam("cat") int cat, @RequestParam("image") MultipartFile image) throws URISyntaxException, StorageException {

        if(postService.findPostById(blogObject.getId()) != null){

            BlogPost toUpdate = postService.findPostById(blogObject.getId());

            updatePost(toUpdate, blogObject, image, cat);

            ra.addFlashAttribute("mSg", "POST UPDATED!");

            return "redirect:/blog";
        } else {
            Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = "";

            if(p instanceof UserDetails){
                username = ((UserDetails) p).getUsername();
            } else {
                username = p.toString();
            }

            SiteAdmin author = adminService.getUserByUsername(username);

            BlogPost toSave = blogObject;
            BlogPostCategory c = postService.findCatById(cat);
            System.out.println("CAT ID :" + cat + " IS: " + c.getName());
            toSave.setCategory(c);
            toSave.setAuthor(author);

            if(!image.isEmpty()){
                String url = storageService.getSavedImageUrl("blog", image);
                if(url != null){
                    toSave.setImageUrl(url);
                }
            }

            if(postService.savePost(toSave)){
                ra.addFlashAttribute("mSg", "Post Saved Successfully!");
            } else {
                ra.addFlashAttribute("mSg", "ERROR SAVING POST!");
                ra.addFlashAttribute("blogObject", toSave);
                return "redirect:/admin/blogs/new";
            }

            return "redirect:/blog";
        }

    }


    public void updatePost(BlogPost toUpdate, BlogPost newPost, MultipartFile file, int newCat) throws URISyntaxException, StorageException {

        if(!toUpdate.getTitle().equals(newPost.getTitle())){
            toUpdate.setTitle(newPost.getTitle());
        }

        if(toUpdate.getCategory().getId() != newCat){
            toUpdate.setCategory(postService.findCatById(newCat));
        }

        if(!toUpdate.getExerpt().equals(newPost.getExerpt())){
            toUpdate.setExerpt(newPost.getExerpt());
        }

        if(!toUpdate.getContent().equals(newPost.getContent())){
            toUpdate.setContent(newPost.getContent());
        }

        if(file.getSize() > 10){
            String url = storageService.getSavedImageUrl("blog", file);
            toUpdate.setImageUrl(url);
        }

        postService.savePost(toUpdate);
    }

    @GetMapping("/blog/view/{id}")
    public String viewPost(Model m, @PathVariable("id") int id){
        BlogPost post = postService.findPostById(id);

        m.addAttribute("post", post);

        return "blogs_view";
    }

    @GetMapping("/admin/blog/edit/{id}")
    public String editPost(@PathVariable("id") int id, Model m){
        BlogPost p = postService.findPostById(id);

        m.addAttribute("categories", postService.listCategories());
        m.addAttribute("blogObject", p);

        return "blogs_new";
    }

    @GetMapping("/admin/blog/delete/{id}")
    public String deleteBlog(@PathVariable("id") int id, RedirectAttributes ra){
        postService.deletePost(id);
        ra.addFlashAttribute("mSg", "POST DELETED!");

        return "redirect:/blog";
    }
}
