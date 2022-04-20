import { DropDownList } from "@progress/kendo-react-dropdowns";

export const DropDownMenuMarks = ({ values, onChange }) => {
    return (
        <section className="k-my-8">
            <form className="k-form">
                <DropDownList data={values} onChange={e => onChange(e.target)} />
            </form>
        </section>
    );
};