import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfile } from 'app/shared/model/profile.model';
import { getEntities } from './profile.reducer';

export const Profile = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const profileList = useAppSelector(state => state.profile.entities);
  const loading = useAppSelector(state => state.profile.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="profile-heading" data-cy="ProfileHeading">
        <Translate contentKey="developersApp.profile.home.title">Profiles</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="developersApp.profile.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/profile/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="developersApp.profile.home.createLabel">Create new Profile</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {profileList && profileList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="developersApp.profile.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.location">Location</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.status">Status</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.avatarPath">Avatar Path</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.photoPath">Photo Path</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.github">Github</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.linkedin">Linkedin</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.twitter">Twitter</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.createdBy">Created By</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.createdDt">Created Dt</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.modifiedBy">Modified By</Translate>
                </th>
                <th>
                  <Translate contentKey="developersApp.profile.modifiedDt">Modified Dt</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {profileList.map((profile, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/profile/${profile.id}`} color="link" size="sm">
                      {profile.id}
                    </Button>
                  </td>
                  <td>{profile.name}</td>
                  <td>{profile.location}</td>
                  <td>
                    <Translate contentKey={`developersApp.ProfileStatus.${profile.status}`} />
                  </td>
                  <td>{profile.avatarPath}</td>
                  <td>{profile.photoPath}</td>
                  <td>{profile.description}</td>
                  <td>{profile.email}</td>
                  <td>{profile.github}</td>
                  <td>{profile.linkedin}</td>
                  <td>{profile.twitter}</td>
                  <td>{profile.createdBy}</td>
                  <td>{profile.createdDt ? <TextFormat type="date" value={profile.createdDt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{profile.modifiedBy}</td>
                  <td>{profile.modifiedDt ? <TextFormat type="date" value={profile.modifiedDt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/profile/${profile.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/profile/${profile.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/profile/${profile.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="developersApp.profile.home.notFound">No Profiles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Profile;
