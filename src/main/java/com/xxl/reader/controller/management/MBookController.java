package com.xxl.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxl.reader.entity.Book;
import com.xxl.reader.service.BookService;
import com.xxl.reader.service.exception.BussinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
public class MBookController {
    @Resource
    private BookService bookService;
    @GetMapping("/index.html")
    public ModelAndView showBook(){
        return new ModelAndView("/management/book");
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        // 上传目录
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        String[] str = uploadPath.split("%20");
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length; ++i){
            if(i != str.length - 1){
                sb.append(str[i]).append(" ");
            } else {
                sb.append(str[i]);
            }
        }
        uploadPath = sb.toString();
        // 文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        // 扩展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        // 保存文件到upload目录
        file.transferTo(new File(uploadPath + fileName + suffix));
        Map result = new HashMap();
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book){
        Map result = new HashMap();
        try{
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0f);
            // 解析HTML元素获取第一张图片的src
            Document doc = Jsoup.parse(book.getDescription());
            String cover = doc.select("img").first().attr("src");
            book.setCover(cover);
            bookService.createBook(book);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex){
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit){
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 1;
        }
        IPage<Book> pageObject = bookService.paging(null, null, page, limit);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
            result.put("data", pageObject.getRecords()); // data键值对绑定当前页面数据
        result.put("count", pageObject.getTotal()); // count键值对绑定未分页时记录总数
        return result;
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Map selectById(@PathVariable("id") Long bookId){
        Map result = new HashMap();
        Book book = bookService.selectById(bookId);
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", book);
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book){
        Map result = new HashMap();
        try{
            Book rawBook = bookService.selectById(book.getBookId()); // 原始数据
            // 一般对原始数据进行查询更新，修改原来数据的字段，而不是将前端传来的数据进行更新
            rawBook.setBookName(book.getBookName());
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setCategoryId(book.getCategoryId());
            String cover = Jsoup.parse(book.getDescription()).select("img").first().attr("src");
            rawBook.setDescription(book.getDescription());
            rawBook.setCover(cover);
            bookService.updateBook(rawBook);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex){
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId){
        Map result = new HashMap();
        try{
            bookService.deleteBook(bookId);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex){
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }
}
