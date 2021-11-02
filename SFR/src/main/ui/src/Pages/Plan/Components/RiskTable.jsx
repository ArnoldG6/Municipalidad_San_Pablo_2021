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
            <div>
                <Table hover>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Tipo General</th>
                            <th>Tipo por Área</th>
                            <th>Tipo Específico</th>
                            <th><Button size="sm" variant="success">Agregar Riesgo</Button></th>
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
                                    <td>
                                        <Button variant={sessionStorage.getItem("userRol") === "USER" ? "outline-dark" : "outline-danger"}
                                            onClick={() => this.removeRisk(risk.id)}
                                            disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>Eliminar</Button>
                                    </td>
                                </tr>
                            )
                        })}
                    </tbody>
                </Table>
            </div>
        );
    }
};
export default RiskTable;