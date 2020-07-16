import React from 'react'
import Alert from 'react-bootstrap/Alert'
import Form from 'react-bootstrap/Form'

export default function DefaultInput({
  meta: { touched, error },
  input,
  label,
  ...rest
}) {
  return (
     <Form.Group>
     <Form.Label>{label}</Form.Label>
      <Form.Control {...input} {...rest} />
      {touched && error && <Alert variant={'danger'}>{error}</Alert>}
     </Form.Group>
  )
}
