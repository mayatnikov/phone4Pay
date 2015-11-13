package p4p.front.controllers;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import p4p.front.data.Echo;
import p4p.front.data.TrType;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

//@TODO Сделать асинхронный обмен через redis
//@TODO ввести контроль подписи HMAC1

@Controller
@RequestMapping("/")
public class EchoController {

    /**
     * Для инъекции сообщений через camel
     */
    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ProducerTemplate producerTemplate;

    /**
     * простейший синхронный эхо запрос/ответ отправляется непосредственно с фронт-сервера
     * @param id любое Long число
     * @param currentUser любая строка в ответе будет подменена на текущего пользователя
     * @return
     */

    @RequestMapping(value = "/echo/{id}", method = RequestMethod.GET)
	@ResponseBody
	public String getEcho(@PathVariable("id") Long id, Principal currentUser) {
		return "Echo test: id=["+id+"]; CurrentUser=["+currentUser.getName()+"]";
	}

    /**
     * простейший синхронный эхо запрос/ответ, аналогично getEcho, но входной параметр class Echo
     * @param req любые данные со структурой class Echo
     * @param currentUser   Principal
     * @return модифицированная структура class'a Echo
     */
    @RequestMapping(value = "/echo/req", method = RequestMethod.POST)
    @ResponseBody
    public Echo getEcho2(@RequestBody Echo req, Principal currentUser) {

        Echo resp = new Echo();
        resp.setLogin(currentUser.getName());
        resp.setRequest(req.getRequest());
        resp.setResponse("ECHO for:" + req.getRequest());
        resp.setStartTransaction(req.getStartTransaction());
        resp.setEndTransaction(new Date());
        resp.setTrID(req.getTrID());
        resp.setTrType(TrType.SYNC);
        resp.setTrStatus(3);
        return resp;
    }

    /**
     * простейший синхронный эхо запрос/ответ, проходит по тракту  front -- mq -- back -- (ответ обратно)
     * @param req любые данные в соотвестви со структурой class'а Echo
     * @param currentUser
     * @return модифицированная на BACK-сервере структура class'a Echo
     */
    @RequestMapping(value = "/echo/aqr", method = RequestMethod.POST)
    @ResponseBody
    public Echo getEcho3(@RequestBody Echo req, Principal currentUser) {
        req.setLogin(currentUser.getName());
//        Echo resp = camelContext.createProducerTemplate().requestBody("activemq:p4p-echo-in", req, Echo.class);
//        Echo resp = producerTemplate.requestBody("activemq:p4p-echo-in", req, Echo.class);
        Echo resp = producerTemplate.requestBody("direct:in", req, Echo.class);
        return resp;
    }

    /**
     * простейший асинхронный эхо запрос/ответ, проходит по тракту  front -- mq -- back -- (redis-storage для пользователя)
     * @param req любые данные в соотвестви со структурой class'а Echo
     * @param currentUser
     * @return модифицированная на BACK-сервере структура class'a Echo
     */
    @RequestMapping(value = "/echo/redis", method = RequestMethod.POST)
    @ResponseBody
    public Echo getEchoRedis(@RequestBody Echo req, Principal currentUser) {
        req.setLogin(currentUser.getName());
        producerTemplate.sendBody("activemq:p4p-echo-redis", req);
        return req;
    }

    /**
     * забрать все данные для абонента (запрос обрабатывается через BACK-сервер)
     * @param echo request - максимальное число записей в ответе, если 0 то не ограниченно
     * @param currentUser
     * @return модифицированная на BACK-сервере структура class'a Echo
     */

    @RequestMapping(value = "/echo/results", method = RequestMethod.POST)
    @ResponseBody
    public String getRedisResults(@RequestBody Echo echo, Principal currentUser) {
        echo.setLogin(currentUser.getName());
        String resp = producerTemplate.requestBody("activemq:p4p-echo-result", echo, String.class);
        return resp;
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class, NullPointerException.class })
	@ResponseStatus(reason = "Reason", value = HttpStatus.NOT_FOUND)
	public void handleEmptyResultDataAccessException(
			EmptyResultDataAccessException ex, HttpServletRequest request) {
		return;
	}
}
