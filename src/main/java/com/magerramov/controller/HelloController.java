package com.magerramov.controller;

import com.magerramov.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Controller
public class HelloController {

    @Autowired
    SaleRepository saleRepository;


    @GetMapping("/topProduct")
    public String showTopProduct(@RequestParam(value = "kpp", defaultValue = "1") int kpp,
                       Model model){
        List<String> top = saleRepository.getTopProduct(kpp);
        String message = "";
        if (top.size()==0){
            message = "Пользовать с номером карты: "+kpp+" не зарегестрирован в базе";
        }
        model.addAttribute("message",message);
        model.addAttribute("kpp",kpp);
        model.addAttribute("tops",top);
        return "TopProductForm";
    }

    @GetMapping("/sumSaleInDay")
    public String sumSaleInDayForm(@RequestParam(value = "date", defaultValue = "2000-1-1") String date,
                                       Model model){
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Double sum = saleRepository.saleSum(date1);
        if (sum == null){
            sum = 0.0;
        }
        String sumStr =String.format(Locale.FRANCE, "%,.5f", sum);
        model.addAttribute("date",date);
        model.addAttribute("sum",sumStr);

        return "SumSaleInDayForm";
    }

}
