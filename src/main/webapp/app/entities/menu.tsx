import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/profile">
        <Translate contentKey="global.menu.entities.profile" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/project">
        <Translate contentKey="global.menu.entities.project" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/skill">
        <Translate contentKey="global.menu.entities.skill" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/skill-category">
        <Translate contentKey="global.menu.entities.skillCategory" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/career">
        <Translate contentKey="global.menu.entities.career" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/interest">
        <Translate contentKey="global.menu.entities.interest" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
