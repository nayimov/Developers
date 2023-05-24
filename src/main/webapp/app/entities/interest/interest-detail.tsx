import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './interest.reducer';

export const InterestDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const interestEntity = useAppSelector(state => state.interest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="interestDetailsHeading">
          <Translate contentKey="developersApp.interest.detail.title">Interest</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{interestEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="developersApp.interest.title">Title</Translate>
            </span>
          </dt>
          <dd>{interestEntity.title}</dd>
          <dt>
            <span id="imagePath">
              <Translate contentKey="developersApp.interest.imagePath">Image Path</Translate>
            </span>
          </dt>
          <dd>{interestEntity.imagePath}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="developersApp.interest.description">Description</Translate>
            </span>
          </dt>
          <dd>{interestEntity.description}</dd>
          <dt>
            <Translate contentKey="developersApp.interest.profile">Profile</Translate>
          </dt>
          <dd>{interestEntity.profile ? interestEntity.profile.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/interest" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/interest/${interestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InterestDetail;
