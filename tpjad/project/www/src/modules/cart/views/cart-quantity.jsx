import UilCheck from '@iconscout/react-unicons/icons/uil-check'
import UilTrash from '@iconscout/react-unicons/icons/uil-trash'
import React, { createRef, useState } from 'react'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'

import { CartQuantityWrapper } from '../cart-styles'

export default function CartQuantity({ product, onQuantityChange, onItemDelete }) {
  const [quantity, setQuantity] = useState(product.quantity)
  const [changed, setChanged] = useState(false)
  const quantityValue = createRef()
  const onChange = () => {
    const value = quantityValue.current.value
    setQuantity(value)
    setChanged(true)
  }
  const onAccept = () => {
    onQuantityChange(product, quantity)
  }
  return (
    <CartQuantityWrapper>
      <Form.Control
        style={{ maxWidth: 128, marginLeft: 'auto' }}
        type='number'
        value={quantity}
        placeholder='Eg. 2'
        onChange={onChange}
        ref={quantityValue}
      />
      {changed && <Button size={'sm'} variant={'primary'} onClick={() => onAccept()}>
        <UilCheck />
      </Button>}
      <Button size={'sm'} variant={'danger'} onClick={() => onItemDelete(product.id)}>
        <UilTrash />
      </Button>
    </CartQuantityWrapper>
  )
}
