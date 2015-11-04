package p4p.front.controllers;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import p4p.front.data.Echo;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

//@TODO Сделать асинхронный обмен через redis
//@TODO ввести контроль подписи HMAC1

@Controller
@RequestMapping("/")
public class EchoController {

    @Autowired
    private CamelContext camelContext;

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
     * @param req любые данные в соотвестви со структурой class'а Echo
     * @param currentUser
     * @return модифицированная структура class'a Echo
     */
    @RequestMapping(value = "/echo/req", method = RequestMethod.POST)
    @ResponseBody
    public Echo getEcho2(@RequestBody Echo req, Principal currentUser) {

        Echo resp = new Echo();
        resp.setFrom("Login user:" + currentUser.getName() + "; From:"+ req.getFrom());
        resp.setTo("answer to:" + req.getTo());
        resp.setResponse("answer server:" + req.getRequest());
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
        req.setFrom(currentUser.getName());
        Echo resp = camelContext.createProducerTemplate().requestBody("activemq:p4p-echo-in", req, Echo.class);
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
        req.setFrom(currentUser.getName());
        camelContext.createProducerTemplate().sendBody("activemq:p4p-echo-redis", req);
        return req;
    }

    /**
     * забрать все данные для абонента (запрос обрабатывается через BACK-сервер)
     * @param req любые данные в соотвестви со структурой class'а Echo
     * @param currentUser
     * @return модифицированная на BACK-сервере структура class'a Echo
     */

    @RequestMapping(value = "/echo/results", method = RequestMethod.POST)
    @ResponseBody
    public String getRedisResults(@RequestBody Echo req, Principal currentUser) {
        req.setFrom(currentUser.getName());
        req.setRequest("GetAll");
        Echo resp = camelContext.createProducerTemplate().requestBody("activemq:p4p-echo-result", req, Echo.class);
        return resp.toString();
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class,
			NullPointerException.class })
	@ResponseStatus(reason = "Reason", value = HttpStatus.NOT_FOUND)
	public void handleEmptyResultDataAccessException(
			EmptyResultDataAccessException ex, HttpServletRequest request) {
		return;
	}
}
