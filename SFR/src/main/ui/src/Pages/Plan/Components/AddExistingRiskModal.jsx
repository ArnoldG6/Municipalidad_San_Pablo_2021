import React, { Component } from 'react';
import '../Plan.css';
import { Modal, Button, Table, Form, Accordion } from "react-bootstrap";
import 'react-toastify/dist/ReactToastify.css';
import Pages from '../../../SharedComponents/Pagination/Pages';

class AddExistingRiskModal extends Component {
    constructor(props) {
        super(props);
        this.state = {
            riskIDs: [],
            riskView: [],
            currentPage: 1,
            pageItemAmount: 10
        }
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
        this.updatePage = this.updatePage.bind(this);
        this.handleRiskRender = this.handleRiskRender.bind(this);
        this.updatePageItems = this.updatePageItems.bind(this);
    }

    componentDidMount() {
        this.handleRiskRender();
    }

    componentDidUpdate(prevProps) {
        if (this.props.risks !== prevProps.risks) {
            this.handleRiskRender()
        }
    }

    handleSubmit() {
        this.setState({ riskIDs: [] });
        this.props.closeModal();
        this.props.addRisk(this.state.riskIDs);
    }

    handleSelect(id) {
        let list = this.state.riskIDs;
        if (list.includes(id)) {
            list = list.filter(function (value) { return value !== id });
            this.setState({ riskIDs: list });
        } else {
            list.push(id);
            this.setState({ riskIDs: list });
        }
    }

    /* Pagination */
    updatePage(pageNumber) {
        this.setState({
            currentPage: pageNumber
        }, () => {
            this.handleRiskRender();
        });
    }

    updatePageItems(amount) {
        this.setState({
            pageItemAmount: amount,
            currentPage: 1
        }, () => {
            this.handleRiskRender();
        })
    }

    handleRiskRender() {
        let items = [];
        let itemAmount = this.state.pageItemAmount;
        let pos = (this.state.currentPage - 1) * itemAmount;
        for (let i = 0; i < itemAmount; i++) {
            let item = this.props.risks.at(pos);
            if (typeof item !== 'undefined' && item !== null) {
                items.push(item);
            }
            pos++;
        }
        this.setState({ riskView: items });
    }

    render() {
        let show = this.props.show;
        let closeModal = this.props.closeModal;
        return (
            <div>
                {/* Mobile */}
                <Modal className='d-lg-none' show={show} onHide={() => { this.setState({ riskIDs: [] }); closeModal(); }}>
                    <Modal.Header closeButton>
                        Seleccione los Riesgos que desea agregar al Plan
                    </Modal.Header>
                    <Modal.Body >
                        {(typeof this.props.risks === 'undefined' || this.props.risks === null) ? <h1>No se han agregado riesgos</h1> :
                            this.props.risks.length === 0 ? <h1>No hay riesgos disponibles</h1> :
                                <div>
                                    <Table>
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th></th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {this.state.riskView.map((risk) => {
                                                return (
                                                    <tr key={risk.id}>
                                                        <td className="align-middle">
                                                            <h3><Form.Check
                                                                aria-label="Seleccionar Riesgo"
                                                                name="selectRisk"
                                                                value={risk.pkID}
                                                                checked={this.state.riskIDs.find(e => e === risk.pkID)}
                                                                onClick={() => { this.handleSelect(risk.pkID) }} /></h3>
                                                        </td>
                                                        <td>
                                                            <Accordion flush>
                                                                <Accordion.Item eventKey={risk.id}>
                                                                    <Accordion.Header>
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
                                                                    </Accordion.Body>
                                                                </Accordion.Item>
                                                            </Accordion>
                                                        </td>
                                                    </tr>
                                                )
                                            })}
                                        </tbody>
                                    </Table>
                                    <Pages
                                        listLength={this.props.risks.length}
                                        itemAmount={this.state.pageItemAmount}
                                        updatePage={this.updatePage}
                                        currentPage={this.state.currentPage}
                                        updatePageItems={this.updatePageItems} />

                                </div>
                        }

                        <div className="text-center">
                            <Button className='btn-sfr' onClick={this.handleSubmit}
                                disabled={((typeof this.props.risks === 'undefined' || this.props.risks === null) ? true :
                                    this.props.risks.length === 0 ? true : false) || this.state.riskIDs.length === 0}>
                                Guardar
                            </Button>
                        </div>
                    </Modal.Body>
                </Modal>
                {/* PC */}
                <Modal className="d-none d-lg-block" id="ModalAddRisk" show={show} onHide={() => { this.setState({ riskIDs: [] }); closeModal(); }}>
                    <Modal.Header closeButton>
                        Seleccione los Riesgos que desea agregar al Plan
                    </Modal.Header>
                    <Modal.Body>
                        {(typeof this.props.risks === 'undefined' || this.props.risks === null) ? <h1>No se han agregado riesgos</h1> :
                            (this.props.risks.length === 0) ? <h1>No hay riesgos disponibles</h1> :
                                <div>
                                    <Table>
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>ID</th>
                                                <th>Nombre</th>
                                                <th>Tipo General</th>
                                                <th>Tipo por Área</th>
                                                <th>Tipo Específico</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {this.state.riskView.map((risk) => {
                                                return (
                                                    <tr key={risk.id}>
                                                        <td className="align-middle">
                                                            <h5>
                                                                <Form.Check
                                                                    aria-label="Seleccionar Riesgo"
                                                                    name="selectRisk"
                                                                    value={risk.pkID}
                                                                    checked={this.state.riskIDs.find(e => e === risk.pkID)}
                                                                    onClick={() => { this.handleSelect(risk.pkID) }} />
                                                            </h5>
                                                        </td>
                                                        <td>{risk.id}</td>
                                                        <td className="nameSlot">{risk.name}</td>
                                                        <td>{risk.generalType}</td>
                                                        <td>{risk.areaType}</td>
                                                        <td>{risk.specType}</td>
                                                    </tr>
                                                )
                                            })}
                                        </tbody>
                                    </Table>
                                    <Pages
                                        listLength={this.props.risks.length}
                                        itemAmount={this.state.pageItemAmount}
                                        updatePage={this.updatePage}
                                        currentPage={this.state.currentPage}
                                        updatePageItems={this.updatePageItems} />
                                </div>
                        }
                        <div className="text-center">
                            <Button className='btn-sfr' onClick={this.handleSubmit}
                                disabled={((typeof this.props.risks === 'undefined' || this.props.risks === null) ? true :
                                    this.props.risks.length === 0 ? true : false) || this.state.riskIDs.length === 0}>
                                Guardar
                            </Button>
                        </div>
                    </Modal.Body>
                </Modal >
            </div>
        );
    };
};
export default AddExistingRiskModal;