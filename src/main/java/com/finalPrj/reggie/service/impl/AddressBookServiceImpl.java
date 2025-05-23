package com.finalPrj.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.finalPrj.reggie.entity.AddressBook;
import com.finalPrj.reggie.mapper.AddressBookMapper;
import com.finalPrj.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {

}
