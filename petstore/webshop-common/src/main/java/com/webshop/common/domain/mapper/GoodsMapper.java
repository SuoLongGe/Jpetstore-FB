package com.webshop.common.domain.mapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.webshop.common.domain.dos.GoodsDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("goodsmapper")
@Mapper
public interface GoodsMapper extends BaseMapper<GoodsDO>{

    //查询所有商品
     default Page<GoodsDO> searchPageGoods(Long current, Long size) {
        Page<GoodsDO> page = new Page<>(current, size);
        return selectPage(page, null); // 这里没有过滤条件
    }

    //根据关键词模糊查询部分商品
    default Page<GoodsDO> searchPageGoodsBySearchKey(Long current, Long size, String searchkey) {
        // 创建分页对象
        Page<GoodsDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<GoodsDO> wrapper = new LambdaQueryWrapper<>();

        // 如果搜索关键字不为空，进行模糊查询
        if (searchkey != null && !searchkey.isEmpty()) {
            wrapper.like(GoodsDO::getGoodsname, searchkey);  // 按照商品名称进行模糊查询
        }

        // 执行分页查询
        return selectPage(page, wrapper);
    }

    // 根据分类ID分页查询商品
    default Page<GoodsDO> searchPageGoodsByCategory(Long current, Long size, Integer categoryid) {
        // 创建分页对象
        Page<GoodsDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<GoodsDO> wrapper = new LambdaQueryWrapper<>();

        // 添加分类ID的查询条件
        wrapper.eq(GoodsDO::getCategoryid, categoryid);

        // 执行分页查询
        return selectPage(page, wrapper);
    }

    // 根据分类ID和模糊查询关键字分页查询商品
    default Page<GoodsDO> searchPageGoodsByCategoryAndSearchKey(Long current, Long size, Integer categoryid, String searchkey) {
        // 创建分页对象
        Page<GoodsDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<GoodsDO> wrapper = new LambdaQueryWrapper<>();

        // 添加分类ID的查询条件
        wrapper.eq(GoodsDO::getCategoryid, categoryid);

        // 如果有搜索关键词，进行模糊查询
        if (searchkey != null && !searchkey.isEmpty()) {
            wrapper.like(GoodsDO::getGoodsname, searchkey);
        }

        // 执行分页查询
        return selectPage(page, wrapper);
    }

    default GoodsDO getGoodsById(Integer id) {
        LambdaQueryWrapper<GoodsDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GoodsDO::getId, id);
        return selectOne(wrapper);
    }
}
