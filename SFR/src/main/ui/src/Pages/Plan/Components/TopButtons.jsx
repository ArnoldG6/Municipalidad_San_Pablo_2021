import React, { Component } from 'react';
import { Stack, Button } from "react-bootstrap";
import "./TopButtons.css";


class TopButtons extends Component {
    render() {
        return (
            <Stack className="mt-4" direction="horizontal" gap={3}>
                <Button classname="menu-button" >Agregar un riesgo</Button>{' '}
                <Button classname="menu-button" variant="primary">Agregar involucrados</Button>{' '}
                <Button variant="warning">Matriz de riesgos</Button>{' '}
                <Button className="ms-auto" variant="light">Generar reporte</Button>{' '}
                <div className="vr" />
                <Button variant="success">Estado</Button>{' '}
            </Stack>
        );
    }
};
export default TopButtons;
