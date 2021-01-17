package com.lbet;

/*
Загрузка изображения из базы данных
List<ImagesData> imagesDataList = (List<ImagesData>)imagesDataRepo.findAll();
        byte[] arr_null = new byte[100];
        if(imagesDataList.size()>0){
            byte[] encodeBase64 = Base64.encodeBase64(imagesDataList.get(0).getImgData());
            String base64Encoded = new String(encodeBase64, "UTF-8");
            modelMap.put("userImage",base64Encoded);
        }
        else {
            modelMap.put("userImage",arr_null);
        }*/

import com.lbet.models.domain.*;
import com.lbet.models.repos.AddedRatesRepo;
import com.lbet.models.repos.ImagesDataRepo;
import com.lbet.models.repos.UserInfoRepo;
import com.lbet.models.repos.UserRatesRepo;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.lbet.models.userinfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@Scope("session")
public class MainController implements ErrorController {
    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private UserRatesRepo userRatesRepo;

    @Autowired
    private ImagesDataRepo imagesDataRepo;

    @Autowired
    private AddedRatesRepo addedRatesRepo;

    @Autowired
    private userinfo thisuser;

    @ModelAttribute("user")
    public userinfo user(){return new userinfo();}

    @GetMapping("/")
    public String redirLogin(ModelMap modelMap){
        if(thisuser.getIdUser()!=null)
            return "redirect:main";
        else
            return "redirect:login";
    }

    @GetMapping("/login")
    public String showAuthorizationPage(Map<String,Object> model){
        model.put("infoLog","");
        return "authorizationpage";
    }

    @RequestMapping(value = "/checklogin", method = RequestMethod.POST)
    public String execEnterInSystem(@RequestParam String email, @RequestParam String password, ModelMap model){
        List<UserInfo> userInfoList = userInfoRepo.findFirstByEmailUserAndPasswordUser(email,password);
        if(userInfoList.size()>0){
            thisuser.setNameUser(userInfoList.get(0).getFirstname());
            thisuser.setLastnameUser(userInfoList.get(0).getLastname());
            thisuser.setIdUser(userInfoList.get(0).getUserid());
            thisuser.setRole(userInfoList.get(0).getRole());
            return "redirect:main";
        }else{
            model.put("infoLog","Логин или пароль введен неверно");
            return "authorizationpage";
        }
    }

    @GetMapping("/registration")
    public String showRegistrationPage(Map<String,Object> model){
        model.put("infoReg","");
        return "registrationpage";
    }

    @PostMapping("/registration")
    public String addNewUser(@RequestParam String email,
                             @RequestParam String password,
                             @RequestParam String name,
                             @RequestParam String lastname,
                             @RequestParam String ownerCard,
                             @RequestParam String cvvCode,
                             @RequestParam String numberCard,
                             @RequestParam String expirationDateMounth,
                             @RequestParam String expirationDateYear,
                             @RequestParam("photo") MultipartFile file,
                             ModelMap modelMap){
        List<UserInfo> userInfoList = (List<UserInfo>)userInfoRepo.findAllByEmailUser(email);
        if(userInfoList.size()>0){
            modelMap.put("infoReg","Данная почта уже занята");
            return "registrationpage";
        }else {
            try{
            UserInfo userInfo = new UserInfo(name,lastname,email,password,expirationDateMounth,expirationDateYear,ownerCard,cvvCode,numberCard,1,file.getBytes());
            userInfoRepo.save(userInfo);
            return "redirect:login";
            }catch (Exception ex){
                return "redirect:/errorpage500";
            }
        }
    }

    @GetMapping("/test")
    public String showLoadFile(){
        return "adminpage";
    }

    @PostMapping("/test")
    public String addLoadFile(@RequestParam("photo") MultipartFile multipartFile) throws IOException {
        imagesDataRepo.save(new ImagesData(multipartFile.getBytes()));
        return "";
    }

    @GetMapping("/main")
    public String showMainPage(ModelMap modelMap) throws UnsupportedEncodingException {
        if(thisuser.getIdUser()!=null){
        List<UserRates> userRatesList =(List<UserRates>) userRatesRepo.findAll();
        modelMap.put("allrates",userRatesList);
        if(thisuser.getRole()==0)
            modelMap.put("value_check","button");
        else
            modelMap.put("value_check","hidden");
            return "mainpage";
        }else {
            return "redirect:/login";
        }

    }

    @RequestMapping(value = "/adduserrate/{rateId}")
    public String showAddUserRatePage(@PathVariable long rateId, ModelMap modelMap){
        if(thisuser.getIdUser()!=null){
            if((addedRatesRepo.findFirstByUserIdAndRateId(thisuser.getIdUser(),rateId)).size()>0){
                return "redirect:/erroraddrate";
            }
            List<UserRates> userRates = (List<UserRates>) userRatesRepo.findAllById(Collections.singleton(rateId));
            List<ListCommand> listCommands = new ArrayList<>();
            listCommands.add(new ListCommand(userRates.get(0).getNameFirstCommand()));
            listCommands.add(new ListCommand("Ничья"));
            listCommands.add(new ListCommand(userRates.get(0).getNameSecondCommand()));
            modelMap.put("commandFirst",userRates.get(0).getNameFirstCommand());
            modelMap.put("rateId",rateId);
            modelMap.put("commandSecond",userRates.get(0).getNameSecondCommand());
            modelMap.put("valuesCommand",listCommands);
            modelMap.put("coefFirstCommand",userRates.get(0).getCoefficientWinFirst());
            modelMap.put("coefNoCommand",userRates.get(0).getCoefficientNoWin());
            modelMap.put("coefSecondCommand",userRates.get(0).getCoefficientWinSecond());
            return "adduserratepage";
        }else
            return "redirect:/login";
    }

    @GetMapping(value = "/adduserrate/{rateId}/{selectCommand}/{summa}")
    public String addNewUserRateThenLoadMain(@PathVariable String rateId, ModelMap modelMap, @PathVariable String selectCommand, @PathVariable String summa){
        if(thisuser.getIdUser()!=null){
            addedRatesRepo.save(new AddedUsersRates(thisuser.getIdUser(),Long.parseLong(rateId),selectCommand,Float.parseFloat(summa)));
            return "redirect:/main";
        }else
            return "redirect:/login";
    }

    @GetMapping("/addrate")
    public String showAddRatePage(ModelMap modelMap){
        if(thisuser.getIdUser()==null){
            return "redirect:/login";
        }else{
            if(thisuser.getRole()==0)
                return "addratepage";
            else
                return "redirect:/erroraccess";
        }
    }

    @PostMapping("/addrate")
    public String createNewRate(
            @RequestParam String nameFirstCommand,
            @RequestParam String nameSecondCommand,
            @RequestParam Float coefFirstWin,
            @RequestParam Float coefNoWin,
            @RequestParam Float coefSecondWin,
            @RequestParam String typeSport){
        if(thisuser.getIdUser()==null){
            return "redirect:/login";
        }else{
            if(thisuser.getRole()==0){
                userRatesRepo.save(new UserRates(coefFirstWin,coefNoWin,coefSecondWin,nameFirstCommand,nameSecondCommand,typeSport));
                return "redirect:/main";
            }else
                return "redirect:/erroraccess";

        }
    }

    @GetMapping("/my")
    public String showMyRatePage(ModelMap modelMap){
        if(thisuser.getIdUser()!=null){
            List<AddedUsersRates> userRatesList =(List<AddedUsersRates>) addedRatesRepo.findAllByUserId(thisuser.getIdUser());
            modelMap.put("allrates",userRatesList);
            return "myrate";
        }else {
            return "redirect:/login";
        }
    }

    @GetMapping("/info/{idrate}")
    public String showInfoAboutRate(@PathVariable String idrate, ModelMap modelMap){
        try{
        if(thisuser.getIdUser()!=null){
            UserRates userRates = userRatesRepo.findFirstByRateId(Long.parseLong(idrate));
            modelMap.put("nameFirstCommand",userRates.getNameFirstCommand());
            modelMap.put("nameSecondCommand",userRates.getNameSecondCommand());
            modelMap.put("coefficientWinFirst",userRates.getCoefficientWinFirst());
            modelMap.put("coefficientNoWin",userRates.getCoefficientNoWin());
            modelMap.put("coefficientWinSecond",userRates.getCoefficientWinSecond());
            modelMap.put("typeSport",userRates.getTypeSport());
            return "infomatch";
        }else {
            return "redirect:/login";
        }}catch (Exception exception){
            return "errorpage500";
        }
    }

    @GetMapping("/lk")
    public String showLkPage(ModelMap modelMap){
        try{
            if(thisuser.getIdUser()!=null){
                UserInfo userInfo = userInfoRepo.findFirstByUserid(thisuser.getIdUser());
                modelMap.put("emailUser",userInfo.getEmailUser());
                modelMap.put("firstname",userInfo.getFirstname());
                modelMap.put("lastname",userInfo.getLastname());
                modelMap.put("passwordUser",userInfo.getPasswordUser());
                byte[] arr_null = new byte[100];
                if(userInfo.getImageAvatar().length>0){
                    byte[] encodeBase64 = Base64.encodeBase64(userInfo.getImageAvatar());
                    String base64Encoded = new String(encodeBase64, "UTF-8");
                    modelMap.put("userImage",base64Encoded);
                }
                else {
                    modelMap.put("userImage",arr_null);
                }
                modelMap.put("ownerCard",userInfo.getOwnerCard());
                modelMap.put("cvvCode",userInfo.getCvvCode());
                modelMap.put("expirationDateMounth",userInfo.getExpirationDateMounth());
                modelMap.put("numberCard",userInfo.getNumberCard());
                modelMap.put("expirationDateYear",userInfo.getExpirationDateYear());
                return "lkpage";
            }else {
                return "redirect:/login";
            }}catch (Exception exception){
            return "errorpage500";
        }
    }

    @PostMapping("/lk")
    public String saveChangeLkPage(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String lastname,
            @RequestParam String ownerCard,
            @RequestParam String cvvCode,
            @RequestParam String numberCard,
            @RequestParam String expirationDateMounth,
            @RequestParam String expirationDateYear,
            @RequestParam("photo") MultipartFile file,
            ModelMap modelMap
    ){
        try{
            if(thisuser.getIdUser()!=null){
                UserInfo userInfo = userInfoRepo.findFirstByUserid(thisuser.getIdUser());
                userInfo.setEmailUser(email);
                userInfo.setPasswordUser(password);
                userInfo.setFirstname(name);
                userInfo.setLastname(lastname);
                userInfo.setOwnerCard(ownerCard);
                userInfo.setCvvCode(cvvCode);
                userInfo.setNumberCard(numberCard);
                userInfo.setExpirationDateMounth(expirationDateMounth);
                userInfo.setExpirationDateYear(expirationDateYear);
                if(!file.isEmpty())
                    userInfo.setImageAvatar(file.getBytes());
                userInfoRepo.save(userInfo);
                return "redirect:/lk";
            }else {
                return "redirect:/login";
            }}catch (Exception exception){
            return "errorpage500";
        }
    }

        @GetMapping("/erroraddrate")
    public String showErrorAddRatePage(){
        if(thisuser.getIdUser()!=null){
            return "erroraddpage";
        }else {
            return "redirect:/login";
        }
    }


    @GetMapping("/erroraccess")
    public String showError403Page(){
        return "erroraccesspage";
    }

    @GetMapping("/errorserverpage")
    public String showErrorServerPage(){return "errorpage500";}

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                return "PageFourZeroFour";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "errorpage500";
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
