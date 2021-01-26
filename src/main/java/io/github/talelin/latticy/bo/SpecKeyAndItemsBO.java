package io.github.talelin.latticy.bo;

import io.github.talelin.latticy.model.SpecKeyDO;
import io.github.talelin.latticy.model.SpecValueDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author TaleLin
 */
@Data
public class SpecKeyAndItemsBO {

    private Integer id;

    private String name;

    private String unit;

    private Integer standard;

    private String description;

    private List<SpecValueDO> items;

    public SpecKeyAndItemsBO(SpecKeyDO specKey, List<SpecValueDO> items) {
        BeanUtils.copyProperties(specKey, this);
        this.setItems(items);
    }

}
