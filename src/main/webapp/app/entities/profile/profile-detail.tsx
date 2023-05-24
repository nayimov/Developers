import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './profile.reducer';

export const ProfileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const profileEntity = useAppSelector(state => state.profile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profileDetailsHeading">
          <Translate contentKey="developersApp.profile.detail.title">Profile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{profileEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="developersApp.profile.name">Name</Translate>
            </span>
          </dt>
          <dd>{profileEntity.name}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="developersApp.profile.location">Location</Translate>
            </span>
          </dt>
          <dd>{profileEntity.location}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="developersApp.profile.status">Status</Translate>
            </span>
          </dt>
          <dd>{profileEntity.status}</dd>
          <dt>
            <span id="avatarPath">
              <Translate contentKey="developersApp.profile.avatarPath">Avatar Path</Translate>
            </span>
          </dt>
          <dd>{profileEntity.avatarPath}</dd>
          <dt>
            <span id="photoPath">
              <Translate contentKey="developersApp.profile.photoPath">Photo Path</Translate>
            </span>
          </dt>
          <dd>{profileEntity.photoPath}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="developersApp.profile.description">Description</Translate>
            </span>
          </dt>
          <dd>{profileEntity.description}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="developersApp.profile.email">Email</Translate>
            </span>
          </dt>
          <dd>{profileEntity.email}</dd>
          <dt>
            <span id="github">
              <Translate contentKey="developersApp.profile.github">Github</Translate>
            </span>
          </dt>
          <dd>{profileEntity.github}</dd>
          <dt>
            <span id="linkedin">
              <Translate contentKey="developersApp.profile.linkedin">Linkedin</Translate>
            </span>
          </dt>
          <dd>{profileEntity.linkedin}</dd>
          <dt>
            <span id="twitter">
              <Translate contentKey="developersApp.profile.twitter">Twitter</Translate>
            </span>
          </dt>
          <dd>{profileEntity.twitter}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="developersApp.profile.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{profileEntity.createdBy}</dd>
          <dt>
            <span id="createdDt">
              <Translate contentKey="developersApp.profile.createdDt">Created Dt</Translate>
            </span>
          </dt>
          <dd>{profileEntity.createdDt ? <TextFormat value={profileEntity.createdDt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="modifiedBy">
              <Translate contentKey="developersApp.profile.modifiedBy">Modified By</Translate>
            </span>
          </dt>
          <dd>{profileEntity.modifiedBy}</dd>
          <dt>
            <span id="modifiedDt">
              <Translate contentKey="developersApp.profile.modifiedDt">Modified Dt</Translate>
            </span>
          </dt>
          <dd>{profileEntity.modifiedDt ? <TextFormat value={profileEntity.modifiedDt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profile/${profileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfileDetail;
