import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './career.reducer';

export const CareerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const careerEntity = useAppSelector(state => state.career.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="careerDetailsHeading">
          <Translate contentKey="developersApp.career.detail.title">Career</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{careerEntity.id}</dd>
          <dt>
            <span id="step">
              <Translate contentKey="developersApp.career.step">Step</Translate>
            </span>
          </dt>
          <dd>{careerEntity.step}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="developersApp.career.description">Description</Translate>
            </span>
          </dt>
          <dd>{careerEntity.description}</dd>
          <dt>
            <span id="imagePath">
              <Translate contentKey="developersApp.career.imagePath">Image Path</Translate>
            </span>
          </dt>
          <dd>{careerEntity.imagePath}</dd>
          <dt>
            <span id="synopsis">
              <Translate contentKey="developersApp.career.synopsis">Synopsis</Translate>
            </span>
          </dt>
          <dd>{careerEntity.synopsis}</dd>
          <dt>
            <Translate contentKey="developersApp.career.profile">Profile</Translate>
          </dt>
          <dd>{careerEntity.profile ? careerEntity.profile.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/career" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/career/${careerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CareerDetail;
