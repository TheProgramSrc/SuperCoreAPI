/*
 * Copyright (c) 2020.
 * Created by TheProgramSrc
 */

package xyz.TheProgramSrc.SuperCoreAPI.gui.precreated.settings.categories;

import xyz.TheProgramSrc.SuperCoreAPI.gui.buttons.actions.ClickAction;
import xyz.TheProgramSrc.SuperCoreAPI.utils.XMaterial;

public abstract class SettingCategory {

    private XMaterial material;
    private String name, description;

    public SettingCategory(XMaterial material, String name, String description) {
        this.material = material;
        this.name = name;
        this.description = description;
    }

    public XMaterial getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void onClick(ClickAction action);
}
