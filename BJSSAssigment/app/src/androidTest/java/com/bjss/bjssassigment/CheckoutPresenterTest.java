package com.bjss.bjssassigment;

import com.bjss.bjssassigment.presenter.CheckoutPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Created by Filippo-TheAppExpert on 7/18/2015.
 */
@RunWith(MockitoJUnitRunner.class)
public class CheckoutPresenterTest {

    @Mock
    private CheckoutPresenter mPresenter;
    @Mock
    private CheckoutPresenter.CheckoutViewListener mView;

    @Before
    public void setUp() throws Exception {
        mPresenter = new CheckoutPresenter(mView);
    }

    @Test
    public void testExchangeRate() throws Exception {
        when(mView.getCurrency()).thenReturn("GPB");

        mPresenter.convertCurrencyInto();

        verify(mView).onExchangeRate(mView.getPrice(), "GPB", "Pound Sterling");
    }
}
