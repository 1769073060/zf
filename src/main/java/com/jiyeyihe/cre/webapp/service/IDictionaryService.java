package com.jiyeyihe.cre.webapp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jiyeyihe.cre.webapp.entity.Dictionary;

public interface IDictionaryService extends IService<Dictionary> {

    /**
     * 根据任意条件查询字典表数据
     * @param key
     * @return
     * @throws Exception
     */
    public String getDictionaryList(String key)throws Exception;


    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param dicKey
     * @return
     * @throws Exception
     */
    public String getDictionaryListPage(Long pageNum,Long pageSize,String dicKey) throws Exception;


}