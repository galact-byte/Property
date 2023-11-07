package com.xszx.sparkweb.controller;

import com.xszx.sparkweb.dao.Top10Dao;
import com.xszx.sparkweb.entity.Top10Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.RequestWrapper;
import java.util.List;

@Controller
public class Top10Controller {
    //Autowired注解用于声明类的属性，属性类型为接口(Top10Dao)类型
    @Autowired
    private Top10Dao top10Dao;

    //RequestMapping注解用于处理URL请求，将URL请求映射到方法中
    @RequestMapping(value = "/top10", produces = "text/html;charset=utf-8")
    public String top10(Model model) {
        List<Top10Entity> top10 = top10Dao.getTop10();
        model.addAttribute("top10", top10);
        return "top10";
    }
}
