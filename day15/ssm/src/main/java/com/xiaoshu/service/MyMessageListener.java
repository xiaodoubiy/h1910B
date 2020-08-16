package com.xiaoshu.service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.dao.TeacherMapper;
import com.xiaoshu.entity.Teacher;

import redis.clients.jedis.Jedis;

public class MyMessageListener implements MessageListener{

	@Autowired
	private TeacherMapper teacherMapper;
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		TextMessage msg=(TextMessage)message;
		try {
			String json = msg.getText();
			System.out.println(json);
			Teacher teacher = JSONObject.parseObject(json, Teacher.class);
			Jedis jedis = new Jedis("127.0.0.1",6379);
			Teacher parm = new Teacher();
			parm.setTname(teacher.getTname());
		    Teacher teacher2 = teacherMapper.selectOne(parm);
		    jedis.hset("信息", teacher.getTname(), teacher2.getTid()+"");
		    
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
