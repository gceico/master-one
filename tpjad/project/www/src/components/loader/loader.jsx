import React from 'react'
import Spinner from 'react-bootstrap/Spinner'

export default function Loader () {
  return (<Spinner animation="border" role="status">
  <span className="sr-only">Loading...</span>
</Spinner>)
}