package ransac;

import java.util.List;

/**
 * @author tiger
 * 
 * @param <T>
 *            Class of the data
 * @param <S>
 *            Class of the parameter
 */
public interface ParameterEstimator<T, S> {
        /**
         * Exact estimation of parameters.
         * 
         * @param data
         *            The data used for the estimate.
         * @param parameters
         *            This vector is cleared and then filled with the computed
         *            parameters.
         */
        public List<S> estimerUnPlan(List<T> data);

        /**
         * Least squares estimation of parameters.
         * 
         * @param data
         *            The data used for the estimate.
         * @param parameters
         *            This vector is cleared and then filled with the computed
         *            parameters.
         */
        public List<S> FonctionMoindreCarre(List<T> data);

        /**
         * This method tests if the given data agrees with the given model
         * parameters.
         * 
         * @param parameters
         *            The estimated model parameters.
         * @param data
         *            The data to be tested.
         */
        public boolean agree(List<S> parameters, T data);
        public Vecteur vectoriel(Vecteur v1,Vecteur v2);
}
