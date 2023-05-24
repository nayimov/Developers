import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './skill.reducer';

export const SkillDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const skillEntity = useAppSelector(state => state.skill.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="skillDetailsHeading">
          <Translate contentKey="developersApp.skill.detail.title">Skill</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{skillEntity.id}</dd>
          <dt>
            <span id="level">
              <Translate contentKey="developersApp.skill.level">Level</Translate>
            </span>
          </dt>
          <dd>{skillEntity.level}</dd>
          <dt>
            <span id="skill">
              <Translate contentKey="developersApp.skill.skill">Skill</Translate>
            </span>
          </dt>
          <dd>{skillEntity.skill}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="developersApp.skill.description">Description</Translate>
            </span>
          </dt>
          <dd>{skillEntity.description}</dd>
          <dt>
            <Translate contentKey="developersApp.skill.profile">Profile</Translate>
          </dt>
          <dd>{skillEntity.profile ? skillEntity.profile.id : ''}</dd>
          <dt>
            <Translate contentKey="developersApp.skill.skillCategory">Skill Category</Translate>
          </dt>
          <dd>{skillEntity.skillCategory ? skillEntity.skillCategory.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/skill" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/skill/${skillEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SkillDetail;
