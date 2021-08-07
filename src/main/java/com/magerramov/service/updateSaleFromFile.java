package com.magerramov.service;


import com.magerramov.models.Product;
import com.magerramov.models.Sale;
import com.magerramov.models.Sales;
import com.magerramov.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class updateSaleFromFile {

    @Autowired
    SaleRepository salesRepository;

    @Value("${directoryInputFile}")
    private String directoryInput;

    @Value("${directoryOutputFile}")
    private String directoryOutput;

    @Value("${parserXMLFileTimeInMills}")
    private long timePause;

    @Scheduled(initialDelay = 5000, fixedDelay = 600000)
    public void unmershellSale(){
        System.out.println("Начал фоновую задачу");
        try {
            // Проверяем наличие необходимых директорий и создаём при отсутствии
            Path inPath = Paths.get(directoryInput);
            Path outPath = Paths.get(directoryOutput);
            if (!Files.exists(inPath)){
                try {
                    Files.createDirectories(inPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!Files.exists(outPath)){
                try {
                    Files.createDirectories(outPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            //Массив файлов из директории входных файлов
            File dir = new File(directoryInput);
            File[] arrFiles = dir.listFiles();
            List<File> lst = Arrays.asList(arrFiles);

            for (File f:lst) {

                // Парсинг XML файла
                Sales sales = XmlToSaleObj(f);



                // Обновление БД
                addSaleInDataBase(sales);

                // Перемещение пропарсеных файлов из директории входных фалов в директорию пропарсеных
                f.renameTo(new File(directoryOutput+"\\"+f.getName()));
                f.delete();
            }
        }catch (JAXBException e) {
            e.printStackTrace();
        }
        System.out.println("Закончил фоновую задачу");
    }

    //Метод добавляет полученные из XML-файла данные в базу данных
    public void addSaleInDataBase(Sales sales){
        for (Sale sale:sales.getSalesList()) {
            Date date = new Date(sale.getXmlDate()/1000*1000L);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");

            String formattedDate = sdf.format(date);

            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(formattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            sale.setDate(date);
            for (Product product:sale.getProducts().getProducts()) {
                sale.addProduct(product);
            }

            salesRepository.save(sale);
        }
    }

    // Функция парсит входящий XML файл и возвращает полученный массив чеков в виде объекта Sales
    public Sales XmlToSaleObj(File file) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Sales.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Sales)unmarshaller.unmarshal(file);
    }

}
