import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SkillCategory from './skill-category';
import SkillCategoryDetail from './skill-category-detail';
import SkillCategoryUpdate from './skill-category-update';
import SkillCategoryDeleteDialog from './skill-category-delete-dialog';

const SkillCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SkillCategory />} />
    <Route path="new" element={<SkillCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<SkillCategoryDetail />} />
      <Route path="edit" element={<SkillCategoryUpdate />} />
      <Route path="delete" element={<SkillCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SkillCategoryRoutes;
