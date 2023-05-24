import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProfile } from 'app/shared/model/profile.model';
import { getEntities as getProfiles } from 'app/entities/profile/profile.reducer';
import { ISkillCategory } from 'app/shared/model/skill-category.model';
import { getEntities as getSkillCategories } from 'app/entities/skill-category/skill-category.reducer';
import { ISkill } from 'app/shared/model/skill.model';
import { SkillLevel } from 'app/shared/model/enumerations/skill-level.model';
import { getEntity, updateEntity, createEntity, reset } from './skill.reducer';

export const SkillUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const profiles = useAppSelector(state => state.profile.entities);
  const skillCategories = useAppSelector(state => state.skillCategory.entities);
  const skillEntity = useAppSelector(state => state.skill.entity);
  const loading = useAppSelector(state => state.skill.loading);
  const updating = useAppSelector(state => state.skill.updating);
  const updateSuccess = useAppSelector(state => state.skill.updateSuccess);
  const skillLevelValues = Object.keys(SkillLevel);

  const handleClose = () => {
    navigate('/skill');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProfiles({}));
    dispatch(getSkillCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...skillEntity,
      ...values,
      profile: profiles.find(it => it.id.toString() === values.profile.toString()),
      skillCategory: skillCategories.find(it => it.id.toString() === values.skillCategory.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          level: 'CORE',
          ...skillEntity,
          profile: skillEntity?.profile?.id,
          skillCategory: skillEntity?.skillCategory?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="developersApp.skill.home.createOrEditLabel" data-cy="SkillCreateUpdateHeading">
            <Translate contentKey="developersApp.skill.home.createOrEditLabel">Create or edit a Skill</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="skill-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('developersApp.skill.level')} id="skill-level" name="level" data-cy="level" type="select">
                {skillLevelValues.map(skillLevel => (
                  <option value={skillLevel} key={skillLevel}>
                    {translate('developersApp.SkillLevel.' + skillLevel)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label={translate('developersApp.skill.skill')}
                id="skill-skill"
                name="skill"
                data-cy="skill"
                type="text"
                validate={{
                  maxLength: { value: 64, message: translate('entity.validation.maxlength', { max: 64 }) },
                }}
              />
              <ValidatedField
                label={translate('developersApp.skill.description')}
                id="skill-description"
                name="description"
                data-cy="description"
                type="text"
                validate={{
                  maxLength: { value: 512, message: translate('entity.validation.maxlength', { max: 512 }) },
                }}
              />
              <ValidatedField
                id="skill-profile"
                name="profile"
                data-cy="profile"
                label={translate('developersApp.skill.profile')}
                type="select"
              >
                <option value="" key="0" />
                {profiles
                  ? profiles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="skill-skillCategory"
                name="skillCategory"
                data-cy="skillCategory"
                label={translate('developersApp.skill.skillCategory')}
                type="select"
              >
                <option value="" key="0" />
                {skillCategories
                  ? skillCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/skill" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default SkillUpdate;
