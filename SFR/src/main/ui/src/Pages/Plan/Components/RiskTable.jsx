import React, { Component } from 'react';
import { Table, Button } from "react-bootstrap";

class RiskTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riesgos: []
        };
        this.removeRisk = this.removeRisk.bind(this);

    }

    removeRisk(id) {
        this.props.removeRisks(id);
    }

    render() {
        return (
            <Table hover>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Tipo General</th>
                        <th>Tipo por Área</th>
                        <th>Tipo Específico</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {this.props.riesgos.map((risk) => {
                        return (
                            <tr key={risk.id}>
                                <td>{risk.id}</td>
                                <td>{risk.name}</td>
                                <td>{risk.generalType}</td>
                                <td>{risk.areaType}</td>
                                <td>{risk.specType}</td>
                                <td><Button variant="outline-danger" onClick={() => this.removeRisk(risk.id)}>Eliminar</Button></td>
                            </tr>
                        )
                    })}
                </tbody>
            </Table>
        );
    }
};
export default RiskTable;