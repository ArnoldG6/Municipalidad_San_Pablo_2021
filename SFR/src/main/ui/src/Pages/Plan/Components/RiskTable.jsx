import React, { Component } from 'react';
import { Button, Accordion } from "react-bootstrap";
import AddExistingRiskModal from './AddExistingRiskModal';
import GenericModal from '../../../SharedComponents/GenericModal/GenericModal';

class RiskTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riesgos: [],
            showRiskModal: false,
            showDel: false,
            delID: ""
        };
        this.removeRisk = this.removeRisk.bind(this);
        this.openModalAddRisk = this.openModalAddRisk.bind(this);
        this.closeModalAddRisk = this.closeModalAddRisk.bind(this);
        this.openModalDelRisk = this.openModalDelRisk.bind(this);
        this.closeModalDelRisk = this.closeModalDelRisk.bind(this);
    }

    removeRisk() {
        this.props.removeRisks(this.state.delID);
        this.closeModalDelRisk();
    }

    openModalAddRisk() {
        this.setState({ showRiskModal: true });
    };

    closeModalAddRisk() {
        this.setState({ showRiskModal: false });
    };

    openModalDelRisk(id) {
        this.setState({ showDel: true, delID: id });
    };

    closeModalDelRisk() {
        this.setState({ showDel: false, delID: "" });
    };

    render() {
        return (
            <div>
                <div className='container-fluid'>
                    <Button size="sm" onClick={this.openModalAddRisk} variant="success" key="AddRiskButtonMobile">
                        <i className="bi bi-plus-square"></i> {' '}
                        Agregar Riesgo
                    </Button>
                    {(typeof this.props.riesgos === 'undefined' || this.props.riesgos === null) ? <h1>No se han agregado riesgos</h1> :
                        this.props.riesgos.length === 0 ? <h1>No se han agregado riesgos</h1> :
                            <Accordion className='mt-2'>
                                {this.props.riesgos.map((risk) => {
                                    return (
                                        <Accordion.Item eventKey={risk.id} key={risk.id}>
                                            <Accordion.Header >
                                                {risk.name}
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <p>
                                                    ID: {risk.id} <br />
                                                    Tipo General: {risk.generalType} <br />
                                                    Tipo por Área: {risk.areaType} <br />
                                                    Tipo Específico: {risk.specType} <br />
                                                    Probabilidad: {risk.probability} <br />
                                                    Impacto: {risk.impact} <br />
                                                    Magnitud: {risk.magnitude} <br />
                                                </p>
                                                <Button variant={sessionStorage.getItem("userRol") === "USER" ? "outline-dark" : "outline-danger"}
                                                    onClick={() => this.openModalDelRisk(risk.pkID)}
                                                    disabled={sessionStorage.getItem("userRol") === "USER" ? true : false}>
                                                    <i className="bi bi-dash-square-fill"></i>{' '}
                                                    Remover Riesgo
                                                </Button>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    );
                                })}
                            </Accordion>
                    }
                </div>
                
                <AddExistingRiskModal
                    risks={this.props.availableRisks}
                    addRisk={this.props.addRisk}
                    show={this.state.showRiskModal}
                    closeModal={this.closeModalAddRisk} />
                <GenericModal
                    show={this.state.showDel}
                    close={this.closeModalDelRisk}
                    action={this.removeRisk}
                    header={"Eliminar Riesgo de un Plan"}
                    body={"¿Esta seguro que desea eliminar este riesgo del Plan?"} />
            </div>
        );
    }
};
export default RiskTable;