import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICareer } from 'app/shared/model/career.model';
import { getEntities } from './career.reducer';

export const Career = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const careerList = useAppSelector(state => state.career.entities);
  const loading = useAppSelector(state => state.career.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="career-heading" data-cy="CareerHeading">
        <Translate contentKey="developersApp.career.home.title">Careers</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="developersApp.career.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/career/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="developersApp.career.home.createLabel">Create new Career</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {careerList && careerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="developersApp.career.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.career.step">Step</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.career.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.career.imagePath">Image Path</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.career.synopsis">Synopsis</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.career.profile">Profile</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {careerList.map((career, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/career/${career.id}`} color="link" size="sm">
                      {career.id}
                    </Button>
                  </td>
                  <td>{career.step}</td>
                  <td>{career.description}</td>
                  <td>{career.imagePath}</td>
                  <td>{career.synopsis}</td>
                  <td>{career.profile ? <Link to={`/profile/${career.profile.id}`}>{career.profile.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/career/${career.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/career/${career.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/career/${career.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="developersApp.career.home.notFound">No Careers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Career;
