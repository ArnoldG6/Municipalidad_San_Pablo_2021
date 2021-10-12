import React, { Component } from 'react';
import { Stack, Button } from "react-bootstrap";

class TopButtons extends Component {
    render() {
        return (
            <Stack className="mt-4" direction="horizontal" gap={3}>
                <Button variant="primary">Agregar un riesgo</Button>{' '}
                <Button variant="primary">Agregar involucrados</Button>{' '}
                <Button variant="warning">Matriz de riesgos</Button>{' '}
                <Button className="ms-auto" variant="success">Estado</Button>{' '}
                <div className="vr" />
                <Button variant="light">Generar reporte</Button>{' '}
            </Stack>
        );
    }
};
export default TopButtons;
