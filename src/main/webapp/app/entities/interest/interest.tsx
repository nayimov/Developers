import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInterest } from 'app/shared/model/interest.model';
import { getEntities } from './interest.reducer';

export const Interest = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const interestList = useAppSelector(state => state.interest.entities);
  const loading = useAppSelector(state => state.interest.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="interest-heading" data-cy="InterestHeading">
        <Translate contentKey="developersApp.interest.home.title">Interests</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="developersApp.interest.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/interest/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="developersApp.interest.home.createLabel">Create new Interest</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {interestList && interestList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="developersApp.interest.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.interest.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.interest.imagePath">Image Path</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.interest.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.interest.profile">Profile</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {interestList.map((interest, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/interest/${interest.id}`} color="link" size="sm">
                      {interest.id}
                    </Button>
                  </td>
                  <td>{interest.title}</td>
                  <td>{interest.imagePath}</td>
                  <td>{interest.description}</td>
                  <td>{interest.profile ? <Link to={`/profile/${interest.profile.id}`}>{interest.profile.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/interest/${interest.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/interest/${interest.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/interest/${interest.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="developersApp.interest.home.notFound">No Interests found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Interest;
