import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISkillCategory } from 'app/shared/model/skill-category.model';
import { getEntities } from './skill-category.reducer';

export const SkillCategory = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const skillCategoryList = useAppSelector(state => state.skillCategory.entities);
  const loading = useAppSelector(state => state.skillCategory.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="skill-category-heading" data-cy="SkillCategoryHeading">
        <Translate contentKey="developersApp.skillCategory.home.title">Skill Categories</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="developersApp.skillCategory.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/skill-category/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="developersApp.skillCategory.home.createLabel">Create new Skill Category</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {skillCategoryList && skillCategoryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="developersApp.skillCategory.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.skillCategory.category">Category</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {skillCategoryList.map((skillCategory, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/skill-category/${skillCategory.id}`} color="link" size="sm">
                      {skillCategory.id}
                    </Button>
                  </td>
                  <td>{skillCategory.category}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/skill-category/${skillCategory.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/skill-category/${skillCategory.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/skill-category/${skillCategory.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="developersApp.skillCategory.home.notFound">No Skill Categories found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default SkillCategory;
