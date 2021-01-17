package com.lbet;

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

/**
 * Главный контроллер приложения. Предоставляет основную логику поведения.
 * @author Elena Romanova
 * @version 1.1.2
 */
@Controller
@Scope("session")
public class MainController implements ErrorController {
    /**
     * Репозиторий {@link UserInfoRepo} для предоставления информации к данным пользователя "UserInfo"
     * @see UserInfo
     */
    @Autowired
    private UserInfoRepo userInfoRepo;

    /**
     * Репозиторий {@link UserRatesRepo} для предоставления информации к данным по ставкам "UserRates"
     * @see UserRates
     */
    @Autowired
    private UserRatesRepo userRatesRepo;

    /**
     * Репозиторий {@link AddedRatesRepo} для предоставления информации к данным пользователя "AddedUsersRates"
     * @see AddedUsersRates
     */
    @Autowired
    private AddedRatesRepo addedRatesRepo;

    /**
     * Репозиторий {@link userinfo} для предоставления информации к данным текущей сессии "thisuser"
     */
    @Autowired
    private userinfo thisuser;

    /**
     * Инициализация thisuser при создании новой сессии
     * @return объект {@link userinfo}
     */
    @ModelAttribute("user")
    public userinfo user(){return new userinfo();}

    /**
     * Необходим для маппинга корневой директории для get-запросов, перенаправляет на /main если пользователь уже был
     * авторизован, иначе на страницу /login
     * @return возвращает redirect с адресом страницы
     */
    @GetMapping("/")
    public String redirLogin(){
        if(thisuser.getIdUser()!=null)
            return "redirect:main";
        else
            return "redirect:login";
    }

    /**
     * Необходим для маппинга страницы с авторизацией для get-запросов.
     * @param model необходим для вставки различных объектов в атрибуты страницы
     * @return страницу авторизации
     */
    @GetMapping("/login")
    public String showAuthorizationPage(Map<String,Object> model){
        model.put("infoLog","");
        return "authorizationpage";
    }

    /**
     * еобходим для маппинга страницы с авторизацией для post-запросов.
     * @param email  email пользователя
     * @param password пароль пользователя
     * @param model необходим для вставки различных объектов в атрибуты страницы
     * @return главную страницу, если логин и пароль верные, иначе страницу авторизации.
     */
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

    /**
     * Необходим для маппинга страницы с регистрацией для get-запросов.
     * @param model необходим для вставки различных объектов в атрибуты страницы
     * @return страницу регистрации
     */
    @GetMapping("/registration")
    public String showRegistrationPage(Map<String,Object> model){
        model.put("infoReg","");
        return "registrationpage";
    }

    /**
     * Необходим для маппинга страницы с регистрацией для post-запросов.
     * @param email email пользователя
     * @param password пароль пользователя
     * @param name имя пользователя
     * @param lastname фамилия пользователя
     * @param ownerCard владелец банковской карты
     * @param cvvCode CVV-код карты
     * @param numberCard Номер карты
     * @param expirationDateMounth Срок действия карты (месяц)
     * @param expirationDateYear Срок действия карты (год)
     * @param file фотография пользователя
     * @param modelMap необходим для вставки различных объектов в атрибуты страницы
     * @return главную страницу сайта, если регистрация прошла успешно, иначе страницу регистрации
     * с сообщением о проблеме в форме регистрации
     */
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

    /*ДЛЯ ТЕСТОВ! НЕ ИСПОЛЬЗОВАТЬ ДЛЯ ИНЫХ ЦЕЛЕЙ
    * ------*/
   /* @GetMapping("/test")
    public String showLoadFile(){
        return "adminpage";
    }

    @PostMapping("/test")
    public String addLoadFile(@RequestParam("photo") MultipartFile multipartFile) throws IOException {
        imagesDataRepo.save(new ImagesData(multipartFile.getBytes()));
        return "";
    }
    -------
    */

    /**
     * Необходим для маппинга главной страницы веб-приложения для get-запросов.
     * @param modelMap необходим для вставки различных объектов в атрибуты страницы
     * @return если пользователь авторизирован, то возвращаем главную страницу, иначе страницу с авторизацией
     */
    @GetMapping("/main")
    public String showMainPage(ModelMap modelMap) {
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

    /**
     * Необходим для маппинга страницы с добавлением ставки на матч с get-запросами
     * @param rateId id ставки
     * @param modelMap необходим для вставки различных объектов в атрибуты страницы
     * @return страницу с формой для добавления ставки на матч, если произошла ошибка то перенаправляем
     * на страницу с 500-ой ошибкой, если пользователь не авторизирован, то на страницу с авторизацией
     */
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

    /**
     * Маппит запрос /adduserrate/{rateId}/{selectCommand}/{summa} для регистрации ставки в базе данных
     * @param rateId ИД ставки
     * @param modelMap необходим для вставки различных объектов в атрибуты страницы
     * @param selectCommand Название выбранной команды
     * @param summa сумма ставки
     * @return если пользователь не авторизован, то перенаправляет на страницу с авторизацией, 
     * иначе на главную страницу.
     */
    @GetMapping(value = "/adduserrate/{rateId}/{selectCommand}/{summa}")
    public String addNewUserRateThenLoadMain(@PathVariable String rateId, ModelMap modelMap, @PathVariable String selectCommand, @PathVariable String summa){
        if(thisuser.getIdUser()!=null){
            addedRatesRepo.save(new AddedUsersRates(thisuser.getIdUser(),Long.parseLong(rateId),selectCommand,Float.parseFloat(summa)));
            return "redirect:/main";
        }else
            return "redirect:/login";
    }

    /**
     * Необходим для маппинга страницы с созданием ставки (создавать может только пользователь с правами администратора)
     * на матч с get-запросами
     * @param modelMap необходим для вставки различных объектов в атрибуты страницы
     * @return страницу с формой для создания ставки, если доступ пользователя не соответствует администратору,
     * то перенаправляем на страницу с ошибой доступа
     */
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

    /**
     * Необходим для маппинга страницы с созданием ставки (создавать может только пользователь с правами администратора)
     * на матч с post-запросами для последущего сохранения новой ставки в базу данных.
     * @param nameFirstCommand имя первой команды
     * @param nameSecondCommand имя второй команды
     * @param coefFirstWin коэффциент в случае победы первой команды
     * @param coefNoWin коэффциент в случае ничьи
     * @param coefSecondWin коэффциент в случае победы второй команды
     * @param typeSport тип спорта
     * @return если ставка создана, то перенаправляем на главную страницу сайта ,если доступ пользователя
     * не соответствует администратору, то перенаправляем на страницу с ошибой доступа, если пользователь не авторизован,
     * то на страницу авторизации.
     */
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

    /**
     * Необходим для маппинга страницы с поставленными ставками пользователя для get-запросов
     * @param modelMap необходим для вставки различных объектов в атрибуты страницы
     * @return страницу с поставленными ставками пользователя, если пользователь не авторизован, то на страницу с авторизацией
     */
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

    /**
     * Необходим для маппинга get-запроса для показа информации о ставке пользователя
     * @param idrate ид ставки
     * @param modelMap необходим для вставки различных объектов в атрибуты страницы
     * @return страницу с информацией о ставке пользователя, если возникла ошибка,
     * то перенаправляем на страницу с ошибой доступа, если пользователь не авторизован, то на страницу авторизации.
     */
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

    /**
     * Необходим для маппинга страницы с личным кабинетом с get-запросами
     * @param modelMap необходим для вставки различных объектов в атрибуты страницы
     * @return страницу с личным кабинетом пользователя, если возникла ошибка,
     * то перенаправляем на страницу с ошибой доступа, если пользователь не авторизован, то на страницу авторизации.
     */
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

    /**
     * Необходим для маппинга страницы с личным кабинетом с post-запросами
     * @param email email пользователя
     * @param password пароль пользователя
     * @param name имя пользователя
     * @param lastname фамилия пользователя
     * @param ownerCard владелец банковской карты
     * @param cvvCode CVV-код карты
     * @param numberCard Номер карты
     * @param expirationDateMounth Срок действия карты (месяц)
     * @param expirationDateYear Срок действия карты (год)
     * @param file фотография пользователя
     * @param modelMap необходим для вставки различных объектов в атрибуты страницы
     * @return страницу с личным кабинетом пользователя, если возникла ошибка,
     * то перенаправляем на страницу с ошибой доступа, если пользователь не авторизован, то перенаправляем на страницу авторизации.
     */
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


    /**
     * Необходим для маппинга страницы с информацией об ошибке добавления ставки на матч с get-запросами
     * @return страницу с информацией об ошибке, если пользователь не авторизован, то перенаправляем на страницу авторизации.
     */
    @GetMapping("/erroraddrate")
    public String showErrorAddRatePage(){
        if(thisuser.getIdUser()!=null){
            return "erroraddpage";
        }else {
            return "redirect:/login";
        }
    }


    /**
     * Необходим для маппинга страницы с информацией об ошибке доступа к информации (код ошибки 403) с get-запросами
     * @return страницу с информацией об ошибке 403
     */
    @GetMapping("/erroraccess")
    public String showError403Page(){
        return "erroraccesspage";
    }

    /**
     * Необходим для маппинга страницы с информацией об ошибке сервера (код ошибки 500) с get-запросами
     * @return страницу с информацией об ошибке 500
     */
    @GetMapping("/errorserverpage")
    public String showErrorServerPage(){return "errorpage500";}

    /**
     * Необходим для маппинга страницы с адресом |error. Данный адрес является дефолтным для используемого
     * сервера приложений. При смене сервера приложений необходимо изменить адрес на соответствующий!
     * @param request ответ сервера.
     * @return страницы с информацией об ошибках
     */
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

    /**
     * Метод интерфейса {@link ErrorController}.
     * @return null-значение
     */
    @Override
    public String getErrorPath() {
        return null;
    }
}
