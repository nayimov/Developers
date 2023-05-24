import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Career from './career';
import CareerDetail from './career-detail';
import CareerUpdate from './career-update';
import CareerDeleteDialog from './career-delete-dialog';

const CareerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Career />} />
    <Route path="new" element={<CareerUpdate />} />
    <Route path=":id">
      <Route index element={<CareerDetail />} />
      <Route path="edit" element={<CareerUpdate />} />
      <Route path="delete" element={<CareerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CareerRoutes;
