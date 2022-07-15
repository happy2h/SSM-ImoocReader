package com.xxl.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxl.reader.entity.Evaluation;
import com.xxl.reader.service.EvaluationService;
import com.xxl.reader.service.exception.BussinessException;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/index.html")
    public ModelAndView showEvaluation(){
        return new ModelAndView("/management/evaluation");
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit){
        Map result = new HashMap();
        if(page == null){
            page = 1;
        }
        if(limit == null){
            limit = 1;
        }
        try{
            IPage<Evaluation> pageObject = evaluationService.paging(page, limit);
            result.put("code", "0");
            result.put("msg", "success");
            result.put("data", pageObject.getRecords()); // data键值对绑定当前页面数据
            result.put("count", pageObject.getTotal()); // count键值对绑定未分页时记录总数
        } catch (BussinessException ex){
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @PostMapping("/disable")
    @ResponseBody
    public Map disableEvaluation(Long evaluationId, String reason){
        Map result = new HashMap();
        try{
            evaluationService.disableEvaluation(evaluationId, reason);
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
